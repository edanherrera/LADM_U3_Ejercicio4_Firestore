package mx.tecnm.tepic.ladm_u3_ejercicio4_firestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.FirebaseFirestore
import mx.tecnm.tepic.ladm_u3_ejercicio4_firestore.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val baseRemota = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.insertar.setOnClickListener{

            val datos = hashMapOf(
                "nombre" to binding.nombre.text.toString(),
                "domicilio" to binding.domicilio.text.toString(),
                "edad" to binding.edad.text.toString().toInt()
            )

            baseRemota.collection("persona")
                .add(datos)
                .addOnSuccessListener {
                    Toast.makeText(this, "EXITO SE INSERTO", Toast.LENGTH_LONG)
                        .show()
                }
                .addOnFailureListener{
                    AlertDialog.Builder(this)
                        .setMessage(it.message)
                        .show()
                }
        }
    }
}