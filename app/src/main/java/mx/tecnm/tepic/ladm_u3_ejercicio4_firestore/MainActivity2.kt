package mx.tecnm.tepic.ladm_u3_ejercicio4_firestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.FirebaseFirestore
import mx.tecnm.tepic.ladm_u3_ejercicio4_firestore.databinding.ActivityMain2Binding
import mx.tecnm.tepic.ladm_u3_ejercicio4_firestore.databinding.ActivityMainBinding

class MainActivity2 : AppCompatActivity(){
    var idSeleccionado = ""
    lateinit var binding: ActivityMain2Binding
    val baseRemota = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        idSeleccionado = intent.extras!!.getString("idSeleccionado")!!

        baseRemota.collection("persona")
            .document(idSeleccionado)
            .get()
            .addOnSuccessListener {
                binding.nombre.setText(it.getString("nombre"))
                binding.domicilio.setText(it.getString("domicilio"))
                binding.edad.setText(it.getLong("edad").toString())
            }
            .addOnFailureListener{
                AlertDialog.Builder(this)
                    .setMessage(it.message)
                    .show()
            }
        binding.regresar.setOnClickListener {
            finish()
        }
        binding.actualizar.setOnClickListener {
            baseRemota.collection("persona")
                .document(idSeleccionado)
                .update("nombre" , binding.nombre.text.toString(),
                    "domicilio" , binding.domicilio.text.toString(),
                    "edad" , binding.edad.text.toString().toInt())
                .addOnSuccessListener {
                    Toast.makeText(this, "EXITO SE ACTUALIZÓ", Toast.LENGTH_LONG)
                        .show()
                }
                .addOnFailureListener{
                    AlertDialog.Builder(this)
                        .setMessage(it.message)
                        .show()
                }
            binding.nombre.setText("")
            binding.domicilio.setText("")
            binding.edad.setText("")
        }
    }
}