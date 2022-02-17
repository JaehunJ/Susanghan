package com.susanghan.android.data

enum class Period(val value:Int){
    MonthTotal(0),
    Month3(3),
    Month6(6),
    Month12(12)
}

enum class OrderType(val type:String){
    A("A"), R("R")
}

enum class ClothCategory(val value:String){
    Sweatshirt("티셔츠/맨투맨"),
    Shirts("셔츠/블라우스"),
    One("원피스"),
    Pants("바지"),
    Jean("청바지"),
    Skirts("치마"),
    Etc("기타")
}

enum class ClothCategoryCode(val value:String){
    Sweatshirt("00"),
    Shirts("01"),
    One("02"),
    Pants("03"),
    Jean("04"),
    Skirts("05"),
    Etc("06")
}

enum class ReformStatus(val value:Int){
    Start(2),
    Stop(3)
}


