package com.example.heartdisease

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DB (context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, databaseName, factory, databaseVersion) {

    companion object{
        lateinit var database: SQLiteDatabase

        private val databaseName = "heartdiseaseApp.db"
        private val databaseVersion = 1

        const val heartDiseaseTableName = "HeartDisease"
        const val heartdiseaseColTableId = 0
        const val heartDiseaseColId = 1
        const val heartDiseaseColAge = 2
        const val heartDiseaseColSex = 3
        const val heartDiseaseColCp = 4
        const val heartDiseaseColTrestbps = 5
        const val heartDiseaseColChol = 6
        const val heartDiseaseColFbs = 7
        const val heartDiseaseColRestecg = 8
        const val heartDiseaseColThalach = 9
        const val heartDiseaseColExang = 10
        const val heartDiseaseColOldpeak = 11
        const val heartDiseaseColSlope = 12
        const val heartDiseaseColCa = 13
        const val heartDiseaseColThal = 14
        const val heartDiseaseColOutcome = 15

        val heartdiseaseCols: Array<String> = arrayOf<String>("tableId", "id", "age", "sex", "cp", "trestbps", "chol", "fbs", "restecg", "thalach", "exang", "oldpeak", "slope", "ca", "thal", "outcome")
        const val heartdiseaseNumberCols = 15
    
   }
private val heartdiseaseCreateSchema =
    "create table HeartDisease (tableId integer primary key autoincrement" +
        ", id VARCHAR(50) not null"+
        ", age integer not null"+
        ", sex integer not null"+
        ", cp integer not null"+
        ", trestbps integer not null"+
        ", chol integer not null"+
        ", fbs integer not null"+
        ", restecg integer not null"+
        ", thalach integer not null"+
        ", exang integer not null"+
        ", oldpeak integer not null"+
        ", slope integer not null"+
        ", ca integer not null"+
        ", thal integer not null"+
        ", outcome VARCHAR(50) not null"+
    ")"

    override fun onCreate(db: SQLiteDatabase) {
         db.execSQL(heartdiseaseCreateSchema)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + heartdiseaseCreateSchema)
        onCreate(db)
    }

    fun onDestroy() {
        database.close()
    }

    fun listHeartDisease(): ArrayList<HeartDiseaseVO> {
        val res = ArrayList<HeartDiseaseVO>()
        database = readableDatabase
        val cursor: Cursor =
            database.query(heartDiseaseTableName, heartdiseaseCols, null, null, null, null, null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast()) {
			res.add(setData(cursor))
            cursor.moveToNext()
        }
        cursor.close()
        return res
    }

	fun createHeartDisease(heartdiseasevo: HeartDiseaseVO) {
        database = writableDatabase
        database.insert(heartDiseaseTableName, heartdiseaseCols[1], putData(heartdiseasevo))
    }

    fun searchByHeartDiseaseid(value: String): ArrayList<HeartDiseaseVO> {
            val res = ArrayList<HeartDiseaseVO>()
	        database = readableDatabase
	        val args = arrayOf(value)
	        val cursor: Cursor = database.rawQuery(
	            "select tableId, id, age, sex, cp, trestbps, chol, fbs, restecg, thalach, exang, oldpeak, slope, ca, thal, outcome from HeartDisease where id = ?",
	            args
	        )
	        cursor.moveToFirst()
	        while (!cursor.isAfterLast()) {
				res.add(setData(cursor))
	            cursor.moveToNext()
	        }
	        cursor.close()
	        return res
	    }
	     
    fun searchByHeartDiseaseage(value: String): ArrayList<HeartDiseaseVO> {
            val res = ArrayList<HeartDiseaseVO>()
	        database = readableDatabase
	        val args = arrayOf(value)
	        val cursor: Cursor = database.rawQuery(
	            "select tableId, id, age, sex, cp, trestbps, chol, fbs, restecg, thalach, exang, oldpeak, slope, ca, thal, outcome from HeartDisease where age = ?",
	            args
	        )
	        cursor.moveToFirst()
	        while (!cursor.isAfterLast()) {
				res.add(setData(cursor))
	            cursor.moveToNext()
	        }
	        cursor.close()
	        return res
	    }
	     
    fun searchByHeartDiseasesex(value: String): ArrayList<HeartDiseaseVO> {
            val res = ArrayList<HeartDiseaseVO>()
	        database = readableDatabase
	        val args = arrayOf(value)
	        val cursor: Cursor = database.rawQuery(
	            "select tableId, id, age, sex, cp, trestbps, chol, fbs, restecg, thalach, exang, oldpeak, slope, ca, thal, outcome from HeartDisease where sex = ?",
	            args
	        )
	        cursor.moveToFirst()
	        while (!cursor.isAfterLast()) {
				res.add(setData(cursor))
	            cursor.moveToNext()
	        }
	        cursor.close()
	        return res
	    }
	     
    fun searchByHeartDiseasecp(value: String): ArrayList<HeartDiseaseVO> {
            val res = ArrayList<HeartDiseaseVO>()
	        database = readableDatabase
	        val args = arrayOf(value)
	        val cursor: Cursor = database.rawQuery(
	            "select tableId, id, age, sex, cp, trestbps, chol, fbs, restecg, thalach, exang, oldpeak, slope, ca, thal, outcome from HeartDisease where cp = ?",
	            args
	        )
	        cursor.moveToFirst()
	        while (!cursor.isAfterLast()) {
				res.add(setData(cursor))
	            cursor.moveToNext()
	        }
	        cursor.close()
	        return res
	    }
	     
    fun searchByHeartDiseasetrestbps(value: String): ArrayList<HeartDiseaseVO> {
            val res = ArrayList<HeartDiseaseVO>()
	        database = readableDatabase
	        val args = arrayOf(value)
	        val cursor: Cursor = database.rawQuery(
	            "select tableId, id, age, sex, cp, trestbps, chol, fbs, restecg, thalach, exang, oldpeak, slope, ca, thal, outcome from HeartDisease where trestbps = ?",
	            args
	        )
	        cursor.moveToFirst()
	        while (!cursor.isAfterLast()) {
				res.add(setData(cursor))
	            cursor.moveToNext()
	        }
	        cursor.close()
	        return res
	    }
	     
    fun searchByHeartDiseasechol(value: String): ArrayList<HeartDiseaseVO> {
            val res = ArrayList<HeartDiseaseVO>()
	        database = readableDatabase
	        val args = arrayOf(value)
	        val cursor: Cursor = database.rawQuery(
	            "select tableId, id, age, sex, cp, trestbps, chol, fbs, restecg, thalach, exang, oldpeak, slope, ca, thal, outcome from HeartDisease where chol = ?",
	            args
	        )
	        cursor.moveToFirst()
	        while (!cursor.isAfterLast()) {
				res.add(setData(cursor))
	            cursor.moveToNext()
	        }
	        cursor.close()
	        return res
	    }
	     
    fun searchByHeartDiseasefbs(value: String): ArrayList<HeartDiseaseVO> {
            val res = ArrayList<HeartDiseaseVO>()
	        database = readableDatabase
	        val args = arrayOf(value)
	        val cursor: Cursor = database.rawQuery(
	            "select tableId, id, age, sex, cp, trestbps, chol, fbs, restecg, thalach, exang, oldpeak, slope, ca, thal, outcome from HeartDisease where fbs = ?",
	            args
	        )
	        cursor.moveToFirst()
	        while (!cursor.isAfterLast()) {
				res.add(setData(cursor))
	            cursor.moveToNext()
	        }
	        cursor.close()
	        return res
	    }
	     
    fun searchByHeartDiseaserestecg(value: String): ArrayList<HeartDiseaseVO> {
            val res = ArrayList<HeartDiseaseVO>()
	        database = readableDatabase
	        val args = arrayOf(value)
	        val cursor: Cursor = database.rawQuery(
	            "select tableId, id, age, sex, cp, trestbps, chol, fbs, restecg, thalach, exang, oldpeak, slope, ca, thal, outcome from HeartDisease where restecg = ?",
	            args
	        )
	        cursor.moveToFirst()
	        while (!cursor.isAfterLast()) {
				res.add(setData(cursor))
	            cursor.moveToNext()
	        }
	        cursor.close()
	        return res
	    }
	     
    fun searchByHeartDiseasethalach(value: String): ArrayList<HeartDiseaseVO> {
            val res = ArrayList<HeartDiseaseVO>()
	        database = readableDatabase
	        val args = arrayOf(value)
	        val cursor: Cursor = database.rawQuery(
	            "select tableId, id, age, sex, cp, trestbps, chol, fbs, restecg, thalach, exang, oldpeak, slope, ca, thal, outcome from HeartDisease where thalach = ?",
	            args
	        )
	        cursor.moveToFirst()
	        while (!cursor.isAfterLast()) {
				res.add(setData(cursor))
	            cursor.moveToNext()
	        }
	        cursor.close()
	        return res
	    }
	     
    fun searchByHeartDiseaseexang(value: String): ArrayList<HeartDiseaseVO> {
            val res = ArrayList<HeartDiseaseVO>()
	        database = readableDatabase
	        val args = arrayOf(value)
	        val cursor: Cursor = database.rawQuery(
	            "select tableId, id, age, sex, cp, trestbps, chol, fbs, restecg, thalach, exang, oldpeak, slope, ca, thal, outcome from HeartDisease where exang = ?",
	            args
	        )
	        cursor.moveToFirst()
	        while (!cursor.isAfterLast()) {
				res.add(setData(cursor))
	            cursor.moveToNext()
	        }
	        cursor.close()
	        return res
	    }
	     
    fun searchByHeartDiseaseoldpeak(value: String): ArrayList<HeartDiseaseVO> {
            val res = ArrayList<HeartDiseaseVO>()
	        database = readableDatabase
	        val args = arrayOf(value)
	        val cursor: Cursor = database.rawQuery(
	            "select tableId, id, age, sex, cp, trestbps, chol, fbs, restecg, thalach, exang, oldpeak, slope, ca, thal, outcome from HeartDisease where oldpeak = ?",
	            args
	        )
	        cursor.moveToFirst()
	        while (!cursor.isAfterLast()) {
				res.add(setData(cursor))
	            cursor.moveToNext()
	        }
	        cursor.close()
	        return res
	    }
	     
    fun searchByHeartDiseaseslope(value: String): ArrayList<HeartDiseaseVO> {
            val res = ArrayList<HeartDiseaseVO>()
	        database = readableDatabase
	        val args = arrayOf(value)
	        val cursor: Cursor = database.rawQuery(
	            "select tableId, id, age, sex, cp, trestbps, chol, fbs, restecg, thalach, exang, oldpeak, slope, ca, thal, outcome from HeartDisease where slope = ?",
	            args
	        )
	        cursor.moveToFirst()
	        while (!cursor.isAfterLast()) {
				res.add(setData(cursor))
	            cursor.moveToNext()
	        }
	        cursor.close()
	        return res
	    }
	     
    fun searchByHeartDiseaseca(value: String): ArrayList<HeartDiseaseVO> {
            val res = ArrayList<HeartDiseaseVO>()
	        database = readableDatabase
	        val args = arrayOf(value)
	        val cursor: Cursor = database.rawQuery(
	            "select tableId, id, age, sex, cp, trestbps, chol, fbs, restecg, thalach, exang, oldpeak, slope, ca, thal, outcome from HeartDisease where ca = ?",
	            args
	        )
	        cursor.moveToFirst()
	        while (!cursor.isAfterLast()) {
				res.add(setData(cursor))
	            cursor.moveToNext()
	        }
	        cursor.close()
	        return res
	    }
	     
    fun searchByHeartDiseasethal(value: String): ArrayList<HeartDiseaseVO> {
            val res = ArrayList<HeartDiseaseVO>()
	        database = readableDatabase
	        val args = arrayOf(value)
	        val cursor: Cursor = database.rawQuery(
	            "select tableId, id, age, sex, cp, trestbps, chol, fbs, restecg, thalach, exang, oldpeak, slope, ca, thal, outcome from HeartDisease where thal = ?",
	            args
	        )
	        cursor.moveToFirst()
	        while (!cursor.isAfterLast()) {
				res.add(setData(cursor))
	            cursor.moveToNext()
	        }
	        cursor.close()
	        return res
	    }
	     
    fun searchByHeartDiseaseoutcome(value: String): ArrayList<HeartDiseaseVO> {
            val res = ArrayList<HeartDiseaseVO>()
	        database = readableDatabase
	        val args = arrayOf(value)
	        val cursor: Cursor = database.rawQuery(
	            "select tableId, id, age, sex, cp, trestbps, chol, fbs, restecg, thalach, exang, oldpeak, slope, ca, thal, outcome from HeartDisease where outcome = ?",
	            args
	        )
	        cursor.moveToFirst()
	        while (!cursor.isAfterLast()) {
				res.add(setData(cursor))
	            cursor.moveToNext()
	        }
	        cursor.close()
	        return res
	    }
	     

    fun editHeartDisease(heartdiseasevo: HeartDiseaseVO) {
        database = writableDatabase
        val args = arrayOf(heartdiseasevo.id)
        database.update(heartDiseaseTableName, putData(heartdiseasevo), "id =?", args)
    }

    fun deleteHeartDisease(value: String) {
        database = writableDatabase
        val args = arrayOf(value)
        database.delete(heartDiseaseTableName, "id = ?", args)
    }

	private fun setData(cursor: Cursor): HeartDiseaseVO {
		val heartdiseasevo = HeartDiseaseVO()
		heartdiseasevo.id = cursor.getString(heartDiseaseColId)
		heartdiseasevo.age = cursor.getInt(heartDiseaseColAge)
		heartdiseasevo.sex = cursor.getInt(heartDiseaseColSex)
		heartdiseasevo.cp = cursor.getInt(heartDiseaseColCp)
		heartdiseasevo.trestbps = cursor.getInt(heartDiseaseColTrestbps)
		heartdiseasevo.chol = cursor.getInt(heartDiseaseColChol)
		heartdiseasevo.fbs = cursor.getInt(heartDiseaseColFbs)
		heartdiseasevo.restecg = cursor.getInt(heartDiseaseColRestecg)
		heartdiseasevo.thalach = cursor.getInt(heartDiseaseColThalach)
		heartdiseasevo.exang = cursor.getInt(heartDiseaseColExang)
		heartdiseasevo.oldpeak = cursor.getInt(heartDiseaseColOldpeak)
		heartdiseasevo.slope = cursor.getInt(heartDiseaseColSlope)
		heartdiseasevo.ca = cursor.getInt(heartDiseaseColCa)
		heartdiseasevo.thal = cursor.getInt(heartDiseaseColThal)
		heartdiseasevo.outcome = cursor.getString(heartDiseaseColOutcome)

		return heartdiseasevo
	}

	private fun putData(heartdiseasevo: HeartDiseaseVO): ContentValues {
		val wr = ContentValues(heartdiseaseNumberCols)
		wr.put(heartdiseaseCols[heartDiseaseColId], heartdiseasevo.id)
		wr.put(heartdiseaseCols[heartDiseaseColAge], heartdiseasevo.age)
		wr.put(heartdiseaseCols[heartDiseaseColSex], heartdiseasevo.sex)
		wr.put(heartdiseaseCols[heartDiseaseColCp], heartdiseasevo.cp)
		wr.put(heartdiseaseCols[heartDiseaseColTrestbps], heartdiseasevo.trestbps)
		wr.put(heartdiseaseCols[heartDiseaseColChol], heartdiseasevo.chol)
		wr.put(heartdiseaseCols[heartDiseaseColFbs], heartdiseasevo.fbs)
		wr.put(heartdiseaseCols[heartDiseaseColRestecg], heartdiseasevo.restecg)
		wr.put(heartdiseaseCols[heartDiseaseColThalach], heartdiseasevo.thalach)
		wr.put(heartdiseaseCols[heartDiseaseColExang], heartdiseasevo.exang)
		wr.put(heartdiseaseCols[heartDiseaseColOldpeak], heartdiseasevo.oldpeak)
		wr.put(heartdiseaseCols[heartDiseaseColSlope], heartdiseasevo.slope)
		wr.put(heartdiseaseCols[heartDiseaseColCa], heartdiseasevo.ca)
		wr.put(heartdiseaseCols[heartDiseaseColThal], heartdiseasevo.thal)
		wr.put(heartdiseaseCols[heartDiseaseColOutcome], heartdiseasevo.outcome)

		return wr
	}

}
