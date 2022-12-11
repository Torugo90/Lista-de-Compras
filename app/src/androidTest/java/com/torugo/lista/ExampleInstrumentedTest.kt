package com.torugo.lista

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.torugo.lista.data.db.AppDatabase
import com.torugo.lista.data.db.ListaEntity
import com.torugo.lista.data.db.dao.ItensDAO
import com.torugo.lista.data.db.dao.ListaDAO
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private lateinit var listaDao: ListaDAO
    private lateinit var itemDao: ItensDAO
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        listaDao = db.listaDao()
        itemDao = db.itensDao()
    }

    @After
    @Throws(IOException::class)
    fun deleteDb() {
        db.close()
    }


    @Test
    @Throws(Exception::class)
    fun insertAndGetTodo() = runBlocking {
        val lista = ListaEntity(
            id = 100000000000000000,
            nome = "teste",
            texto = "ok",
            total = 25.90,
            dataCriacao = "data criação",
            dataFinalizacao = "data finalização"
        )
        listaDao.save(lista)
        val oneItem = listaDao.getList(100000000000000000)
        assertEquals(oneItem.id, 100000000000000000)
    }
}