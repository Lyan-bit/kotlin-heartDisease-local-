package com.example.heartdisease

import android.content.Context
import java.util.ArrayList
import android.content.res.AssetManager
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel
import java.util.*
import kotlin.Comparator


class ModelFacade private constructor(context: Context) {

    private var db: DB
    private val assetManager: AssetManager = context.assets
    private var fileSystem: FileAccessor

    private var currentHeartDisease: HeartDiseaseVO? = null
	private var currentHeartDiseases: ArrayList<HeartDiseaseVO> = ArrayList()

    init {
    	//init
        db = DB(context, null)
        fileSystem = FileAccessor(context)
	}

    companion object {
        private var instance: ModelFacade? = null
        fun getInstance(context: Context): ModelFacade {
            return instance ?: ModelFacade(context)
        }
    }
    
    fun createHeartDisease(x: HeartDiseaseVO) { 
			 db.createHeartDisease(x)
			 currentHeartDisease = x
	}
			    
    fun editHeartDisease(x: HeartDiseaseVO) {
        db.editHeartDisease(x)
        currentHeartDisease = x
	}

	   fun setSelectedHeartDisease(x: HeartDiseaseVO) {
		    currentHeartDisease = x
	}
		    
		    
	 fun deleteHeartDisease(id: String) {
			 db.deleteHeartDisease(id)
			 currentHeartDisease = null
	 }
			
    fun searchHeartDiseaseByAge(age: Int) : ArrayList<HeartDisease> {
		val itemsList: ArrayList<HeartDisease> = ArrayList()
			currentHeartDiseases = db.listHeartDisease()
			for (x in currentHeartDiseases.indices) {
				if ( currentHeartDiseases[x].getAge() == age) {
					val vo: HeartDiseaseVO = currentHeartDiseases[x]
				    val itemx = HeartDisease.createByPKHeartDisease(vo.getId())
					    itemx.id = vo.getId()
				    itemx.age = vo.getAge()
				    itemx.sex = vo.getSex()
				    itemx.cp = vo.getCp()
				    itemx.trestbps = vo.getTrestbps()
				    itemx.chol = vo.getChol()
				    itemx.fbs = vo.getFbs()
				    itemx.restecg = vo.getRestecg()
				    itemx.thalach = vo.getThalach()
				    itemx.exang = vo.getExang()
				    itemx.oldpeak = vo.getOldpeak()
				    itemx.slope = vo.getSlope()
				    itemx.ca = vo.getCa()
				    itemx.thal = vo.getThal()
				    itemx.outcome = vo.getOutcome()
					itemsList.add(itemx)
				}
			}
			return itemsList
		}
	fun searchHeartDiseaseByAge(): ArrayList<String> {
		currentHeartDiseases = db.listHeartDisease()
		val res: ArrayList<String> = ArrayList()
		for (x in currentHeartDiseases.indices) {
			res.add(currentHeartDiseases[x].getAge().toString())
		}
		return res
	}
	
    fun classifyHeartDisease(heartDisease: HeartDisease) : String {
	    var result : String
		lateinit var tflite : Interpreter
	    lateinit var tflitemodel : ByteBuffer
	
	    try{
		    tflitemodel = loadModelFile(assetManager, "heartDisease.tflite")
	    	tflite = Interpreter(tflitemodel) 
	    } catch(ex: Exception){
		  ex.printStackTrace()
	    }
	        
	    val inputVal: FloatArray = floatArrayOf(
	            ((heartDisease.age - 29) / (77 - 29)).toFloat(),
	            (heartDisease.sex).toFloat(),
	            ((heartDisease.cp - 0) / (3 - 0)).toFloat(),
	            ((heartDisease.trestbps - 94) / (200 - 94)).toFloat(),
	            ((heartDisease.chol - 126) / (564 - 126)).toFloat(),
	            (heartDisease.fbs).toFloat(),
	            ((heartDisease.restecg - 0) / (2 - 0)).toFloat(),
	            ((heartDisease.thalach - 71) / (202 - 71)).toFloat(),
	            (heartDisease.exang).toFloat(),
	            ((heartDisease.oldpeak - 0) / (6.2 - 0)).toFloat(),
	            ((heartDisease.slope - 0) / (2 - 0)).toFloat(),
	            ((heartDisease.ca - 0) / (4 - 0)).toFloat(),
	            ((heartDisease.thal - 0) / (3 - 0)).toFloat()
	        )
	    val outputVal: ByteBuffer = ByteBuffer.allocateDirect(8)
	    outputVal.order(ByteOrder.nativeOrder())
	    tflite.run(inputVal, outputVal)
	    outputVal.rewind()
	        
	  	val labelsList : List<String> = listOf ("positive","negative")
	    val output = FloatArray(2)
	        for (i in 0..1) {
	            output[i] = outputVal.float
	        }
	        
	    result = getSortedResult(output, labelsList)[0].toString()
	        
	        heartDisease.outcome = result
	        persistHeartDisease(heartDisease)
	        
	     return result
	    }
	    
    data class Recognition(
	     var id: String = "",
	     var title: String = "",
	     var confidence: Float = 0F
	     )  {
		override fun toString(): String {
		     return "$title ($confidence%)"
		}
	}
	    
	private fun getSortedResult(labelProbArray: FloatArray, labelList: List<String>): List<Recognition> {
	    
	       val pq = PriorityQueue(
	           labelList.size,
	           Comparator<Recognition> {
	                   (_, _, confidence1), (_, _, confidence2)
	                 -> confidence1.compareTo(confidence2) * -1
	           })
	    
	      for (i in labelList.indices) {
	           val confidence = labelProbArray[i]
	           pq.add(
	               Recognition("" + i,
	                   if (labelList.size > i) labelList[i] else "Unknown", confidence*100))
	            }
	           val recognitions = ArrayList<Recognition>()
	           val recognitionsSize = Math.min(pq.size, labelList.size)
	    
	           if (pq.isNotEmpty()) {
	               for (i in 0 until recognitionsSize) {
	                   recognitions.add(pq.poll())
	               }
	           }
	           else {
	               recognitions.add(Recognition("0", "Unknown",100F))
	           }
	           return recognitions
	}
	        	   
	private fun loadModelFile(assetManager: AssetManager, modelPath: String): ByteBuffer {
        val fileDescriptor = assetManager.openFd(modelPath)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(
            FileChannel.MapMode.READ_ONLY,
            startOffset, declaredLength)
    }


	fun listHeartDisease(): ArrayList<HeartDiseaseVO> {
        currentHeartDiseases = db.listHeartDisease()
		
        return currentHeartDiseases
	}

	fun listAllHeartDisease(): ArrayList<HeartDisease> {	
		currentHeartDiseases = db.listHeartDisease()
		var res = ArrayList<HeartDisease>()
			for (x in currentHeartDiseases.indices) {
					val vo: HeartDiseaseVO = currentHeartDiseases[x]
				    val itemx = HeartDisease.createByPKHeartDisease(vo.getId())
	            itemx.id = vo.getId()
            itemx.age = vo.getAge()
            itemx.sex = vo.getSex()
            itemx.cp = vo.getCp()
            itemx.trestbps = vo.getTrestbps()
            itemx.chol = vo.getChol()
            itemx.fbs = vo.getFbs()
            itemx.restecg = vo.getRestecg()
            itemx.thalach = vo.getThalach()
            itemx.exang = vo.getExang()
            itemx.oldpeak = vo.getOldpeak()
            itemx.slope = vo.getSlope()
            itemx.ca = vo.getCa()
            itemx.thal = vo.getThal()
            itemx.outcome = vo.getOutcome()
			res.add(itemx)
		}
		return res
	}

    fun stringListHeartDisease(): ArrayList<String> {
        currentHeartDiseases = db.listHeartDisease()
        val res: ArrayList<String> = ArrayList()
        for (x in currentHeartDiseases.indices) {
            res.add(currentHeartDiseases[x].toString())
        }
        return res
    }

    fun getHeartDiseaseByPK(value: String): HeartDisease? {
        val res: ArrayList<HeartDiseaseVO> = db.searchByHeartDiseaseid(value)
	        return if (res.isEmpty()) {
	            null
	        } else {
	            val vo: HeartDiseaseVO = res[0]
	            val itemx = HeartDisease.createByPKHeartDisease(value)
            itemx.id = vo.getId()
            itemx.age = vo.getAge()
            itemx.sex = vo.getSex()
            itemx.cp = vo.getCp()
            itemx.trestbps = vo.getTrestbps()
            itemx.chol = vo.getChol()
            itemx.fbs = vo.getFbs()
            itemx.restecg = vo.getRestecg()
            itemx.thalach = vo.getThalach()
            itemx.exang = vo.getExang()
            itemx.oldpeak = vo.getOldpeak()
            itemx.slope = vo.getSlope()
            itemx.ca = vo.getCa()
            itemx.thal = vo.getThal()
            itemx.outcome = vo.getOutcome()
	            itemx
	        }
    }
    
    fun retrieveHeartDisease(value: String): HeartDisease? {
        return getHeartDiseaseByPK(value)
    }

    fun allHeartDiseaseIds(): ArrayList<String> {
        currentHeartDiseases = db.listHeartDisease()
        val res: ArrayList<String> = ArrayList()
            for (heartdisease in currentHeartDiseases.indices) {
                res.add(currentHeartDiseases[heartdisease].getId())
            }
        return res
    }

    fun setSelectedHeartDisease(i: Int) {
        if (i < currentHeartDiseases.size) {
            currentHeartDisease = currentHeartDiseases[i]
        }
    }

    fun getSelectedHeartDisease(): HeartDiseaseVO? {
        return currentHeartDisease
    }

    fun persistHeartDisease(x: HeartDisease) {
        val vo = HeartDiseaseVO(x)
        db.editHeartDisease(vo)
        currentHeartDisease = vo
    }
	

		
}
