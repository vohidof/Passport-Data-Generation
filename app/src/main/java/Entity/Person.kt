package Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class Person : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    var ismi: String? = null

    var familiyasi: String? = null

    var otasiningIsmi: String? = null

    var viloyati: Int? = null

    var shahar: String? = null

    var manzili: String? = null

    var pasportRaqami: String? = null

    var jinsi: Int? = null

    var imagePath: String? = null
}