package usc.dcis.teacherattendancesystem

public class Upload{
    private lateinit var mName:String
    private lateinit var mImageUrl: String

    public fun Upload(){

    }
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

}
