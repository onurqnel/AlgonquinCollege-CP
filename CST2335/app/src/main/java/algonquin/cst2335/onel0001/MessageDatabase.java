package algonquin.cst2335.onel0001;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/*
 * Room'a bu Veritabanı sınıfının ChatMessage nesnelerini depolamak için tasarlandığını ve
 * verileri sorgulamak için ChatMessageDAO sınıfını kullandığını söyler.
 * version parametresi, ChatMessage sınıfının yapısını değiştirmeniz gerektiğinde kullanılır.
 * Diyelim ki bir değişken eklediniz veya çıkardınız, ardından veritabanı tablosuna bir sütun eklemeniz veya kaldırmanız gerekecek.
 * Veritabanının kendisini yeniden oluşturması için sürüm numarasını bir artırmanız yeterlidir.
 */

@Database(entities = {ChatMessage.class}, version = 1)
public abstract class MessageDatabase extends RoomDatabase {
    public abstract ChatMessageDAO cmDAO();
}
