package com.example.recyclerview_ex.recyclerview_basic

import java.io.Serializable

data class ProfileData (
    val name : String,
    val age : Int,
    val img : Int
) : Serializable