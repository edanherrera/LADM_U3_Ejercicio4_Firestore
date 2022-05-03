package mx.tecnm.tepic.ladm_u3_ejercicio4_firestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
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

        //EVENTO (SE DISPARA SOLO)
        FirebaseFirestore.getInstance()
            .collection("persona")
            .addSnapshotListener { query, error ->
                if (error!=null){
                    //SI HUBO ERROR!
                    AlertDialog.Builder(this)
                        .setMessage(error.message)
                        .show()
                    return@addSnapshotListener
                }

                val arreglo = ArrayList<String>()
                for (documento in query!!){
                    var cadena = "nombre: ${documento.getString("nombre")}\n" +
                            "Edad:  ${documento.getLong("edad")}"
                    arreglo.add(cadena)
                }

                binding.lista.adapter= ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, arreglo)
            }

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