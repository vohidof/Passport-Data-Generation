package Fragment

import Database.AppDatabase
import Entity.Person
import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.github.florent37.runtimepermission.kotlin.askPermission
import com.vohidov.passportdata.R
import com.vohidov.passportdata.databinding.FragmentFuqaroQoshishBinding
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


class FuqaroQoshishFragment : Fragment() {

    lateinit var binding: FragmentFuqaroQoshishBinding
    lateinit var appDatabase: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFuqaroQoshishBinding.inflate(LayoutInflater.from(context))

        onResume()

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        appDatabase = AppDatabase.getInstance(binding.root.context)
        binding.imgBack.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }

        val listCitizens = appDatabase.personDao().getAllPersons()
        var listSeriya = ArrayList<String>()
        for (citizen in listCitizens) {
            listSeriya.add(citizen.pasportRaqami!!)
        }

        binding.btnSave.setOnClickListener {
            val person = Person()
            person.ismi = binding.edtIsm.text.toString()
            person.familiyasi = binding.edtFamiliyasi.text.toString()
            person.otasiningIsmi = binding.edtOtasiningIsmi.text.toString()
            person.viloyati = binding.spinViloyat.selectedItemPosition
            person.shahar = binding.edtShahar.text.toString()
            person.manzili = binding.edtUyManzil.text.toString()
            person.jinsi = binding.spinJinsi.selectedItemPosition
            person.imagePath = absolutePath

            person.pasportRaqami = randomSeries(listSeriya)

            if (person.ismi != "" && person.familiyasi != "" && person.imagePath != "") {
                val dialog = AlertDialog.Builder(binding.root.context)

                dialog.setMessage("Ma'lumotlaringiz tog'ri ekanligiga ishonchingiz komilmi?")

                dialog.setPositiveButton("No", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        dialog?.cancel()
                    }
                })

                dialog.setNegativeButton("Yes", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        appDatabase.personDao().addPerson(person)
                        Toast.makeText(
                            binding.root.context,
                            "Fuqaro qo'shildi!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })

                dialog.show()

            }


        }
        binding.imgFuqaro.setOnClickListener {
            askPermission(Manifest.permission.READ_EXTERNAL_STORAGE) {
                //all permissions already granted or just granted
                getImageContent.launch("image/*")
            }.onDeclined { e ->
                if (e.hasDenied()) {

                    android.app.AlertDialog.Builder(binding.root.context)
                        .setMessage("Please accept our permissions")
                        .setPositiveButton("yes") { dialog, which ->
                            e.askAgain();
                        } //ask again
                        .setNegativeButton("no") { dialog, which ->
                            dialog.dismiss();
                        }
                        .show();
                }

                if (e.hasForeverDenied()) {
                    //the list of forever denied permissions, user has check 'never ask again'

                    // you need to open setting manually if you really need it
                    e.goToSettings();
                }
            }
        }
    }

    var absolutePath = ""
    val getImageContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri ->
            uri ?: return@registerForActivityResult
            binding.imgFuqaro.setImageURI(uri)
            val resolver = activity?.contentResolver
            val inputStream = resolver?.openInputStream(uri)
            val format = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val file = File(activity?.filesDir, "$format.jpg")
            val fileOutputStream = FileOutputStream(file)
            inputStream?.copyTo(fileOutputStream)
            inputStream?.close()
            fileOutputStream.close()
            absolutePath = file.absolutePath
            Toast.makeText(binding.root.context, "$absolutePath", Toast.LENGTH_SHORT).show()
        }

    fun randomSeries(listSeriya: List<String>): String {
        var seriya = ""
        var a = Random().nextInt(25)
        var b = Random().nextInt(25)
        var q = 0
        for (x in 'A'..'Z') {
            if (q == a) {
                seriya += x
            }
            if (q == b) {
                seriya += x
            }
            q++
        }

        for (i in 0..6) {
            seriya += Random().nextInt(10)
        }
        for (s in listSeriya) {
            if (s == seriya) {
                randomSeries(listSeriya)
                break
            }
        }
        return seriya
    }
}