package com.example.order_food.dtos

import com.fasterxml.jackson.annotation.JsonProperty

data class Welcome(
    val response: Response,
)

data class Response(
    @JsonProperty("GeoObjectCollection")
    val geoObjectCollection: GeoObjectCollection,
)

data class GeoObjectCollection(
    val metaDataProperty: GeoObjectCollectionMetaDataProperty,
    val featureMember: List<FeatureMember>,
)

data class FeatureMember(
    @JsonProperty("GeoObject")
    val geoObject: GeoObject,
)

data class GeoObject(
    val metaDataProperty: GeoObjectMetaDataProperty,
    val name: String,
    val description:String,
    val boundedBy: BoundedBy,

    @JsonProperty("Point")
    val point: Point,
)

data class BoundedBy(
    @JsonProperty("Envelope")
    val envelope: Envelope,
)

data class Envelope(
    val lowerCorner: String,
    val upperCorner: String,
)

data class GeoObjectMetaDataProperty(
    @JsonProperty("GeocoderMetaData")
    val geocoderMetaData: GeocoderMetaData,
)

data class GeocoderMetaData(
    val precision: String,
    val text: String,
    val kind: String,

    @JsonProperty("Address")
    val address: Address,

    @JsonProperty("AddressDetails")
    val addressDetails: AddressDetails,
)

data class Address(


    val country_code: String,
    val formatted: String,

    @JsonProperty("Components")
    val components: List<Component>,
)

data class Component(
    val kind: String,
    val name: String,
)

data class AddressDetails(
    @JsonProperty("Country")
    val country: Country,
)

data class Country(
    @JsonProperty("AddressLine")
    val addressLine: String,
    @JsonProperty("CountryNameCode")
    val countryNameCode: String,
    @JsonProperty("CountryName")
    val countryName: String,
    @JsonProperty("AdministrativeArea")
    val administrativeArea: AdministrativeArea

)
data class AdministrativeArea(
    @JsonProperty("AdministrativeAreaName")
    val administrativeAreaName: String,
    @JsonProperty("Locality")
    val locality:Locality
)

data class Locality(
    @JsonProperty("LocalityName")
  val  localityName:String,
    @JsonProperty("Thoroughfare")
  val thoroughfare:Thoroughfare
)

data class Thoroughfare(
    @JsonProperty("ThoroughfareName")
    val thoroughfareName:String,
    @JsonProperty("Premise")
    val premise:Premise?=null,
)

data class Premise(
    @JsonProperty("PremiseNumber")
   val premiseNumber:String
)


data class GeoObjectCollectionMetaDataProperty(
    @JsonProperty("GeocoderResponseMetaData")
    val geocoderResponseMetaData: GeocoderResponseMetaData,
)
data class Point(
    val pos: String,
)


data class GeocoderResponseMetaData(
    @JsonProperty("Point")
    val point: Point,

    val request: String,
    val results: String,
    val found: String,
)