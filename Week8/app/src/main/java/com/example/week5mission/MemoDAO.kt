package com.example.week5mission

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MemoDAO {
    @Insert // 삽입
    fun insert(memo: Memo)

    @Delete // 삭제
    fun delete(memo: Memo)

    // 조회
    @Query("SELECT * FROM Memo")
    fun selectAll(): List<Memo> // RoomDB에 있는 모든 유저 정보 가져옴

    @Query("SELECT * FROM Memo WHERE contentsId = :contentsId")
    fun selectByContentsId(contentsId: Int): Memo

    @Query("SELECT * FROM Memo WHERE contents = :contents")
    fun selectbyContentsName(contents: String): Memo

    // 업데이트
    @Query("UPDATE Memo SET contents = :contents WHERE contentsId = :contentsId")
    fun updateContentsByContentsId(contentsId: Int, contents: String)

    @Query("UPDATE Memo SET likes = :likes WHERE contentsId = :contentsId")
    fun updateLikesByContentsId(contentsId: Int, likes: Boolean)
}