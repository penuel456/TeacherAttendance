package usc.dcis.teacherattendancesystem

/*class Upload(trim: String, toString: String) {
     lateinit var mName:String
     lateinit var mImageUrl: String

   /* fun Upload(){

    }*/
    fun Upload(name: String, imageUrl: String) {
        var name = name
        if (name.trim { it <= ' ' } == "") {
            name = "No Name"
        }

        mName = name
        mImageUrl = imageUrl
    }
    fun getName(): String {
        return mName
    }

    fun setName(name: String) {
        mName = name
    }
    fun getImageUrl(): String {
        return mImageUrl
    }

    fun setImageUrl(imageUrl: String) {
        mImageUrl = imageUrl
    }

}*/
class Upload {
    var name: String? = null
    var imageUrl: String? = null

    constructor() {
        //empty constructor needed
    }

    constructor(name: String, imageUrl: String) {
        var name = name
        if (name.trim { it <= ' ' } == "") {
            name = "No Name"
        }

        this.name = name
        this.imageUrl = imageUrl
    }
}