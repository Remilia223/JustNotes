package com.jjewuz.justnotes.Category

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CategoryDao {
    @Insert
    suspend fun insert(category: Category)

    @Query("SELECT * FROM categories")
    fun getAllCategories(): LiveData<List<Category>>

    @Query("DELETE FROM categories WHERE id = :categoryId")
    suspend fun delete(categoryId: Int) {
        if (categoryId != 1) {
            deleteCategoryByIdInternal(categoryId)
        }
    }

    @Query("SELECT name FROM categories WHERE id = :categoryId")
    suspend fun getCategoryNameById(categoryId: Int): String?

    @Query("DELETE FROM categories WHERE id = :categoryId")
    suspend fun deleteCategoryByIdInternal(categoryId: Int)

    @Query("UPDATE categories SET name = :name WHERE id = :categoryId")
    suspend fun updateCategoryName(categoryId: Long, name: String)
}