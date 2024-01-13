package dev.rivu.composeclass1.userslist.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("country")
    val country: String = "",
    @SerializedName("gender")
    val gender: String = "",
    @SerializedName("city")
    val city: String = "",
    @SerializedName("date_of_birth")
    val dateOfBirth: String = "",
    @SerializedName("latitude")
    val latitude: Double = 0.0,
    @SerializedName("last_name")
    val lastName: String = "",
    @SerializedName("profile_picture")
    val profilePicture: String = "",
    @SerializedName("zipcode")
    val zipcode: String = "",
    @SerializedName("phone")
    val phone: String = "",
    @SerializedName("street")
    val street: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("state")
    val state: String = "",
    @SerializedName("job")
    val job: String = "",
    @SerializedName("first_name")
    val firstName: String = "",
    @SerializedName("email")
    val email: String = "",
    @SerializedName("longitude")
    val longitude: Double = 0.0
)