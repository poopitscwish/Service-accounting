package com.example.service_accounting.db

class Record(recordId:Int,id:Int,start:String,end:String,thick:Int,product:String) {
    val ID:Int = recordId
    val FIO_ID: Int = id
    val START: String = start
    val END: String = end
    val THICK: Int = thick
    val PRODUCT: String = product

    fun printInfo(){
        println("$ID\n$FIO_ID\n$START\n$END\n$THICK\n$PRODUCT")
    }
}