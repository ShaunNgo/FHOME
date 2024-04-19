package com.example.fhome.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.example.fhome.Model.BlogItem;
import com.example.fhome.Model.Comment;

import java.util.ArrayList;
import java.util.List;

public class UserDBH extends SQLiteOpenHelper {
    public static final String DBNAME = "Login.db";

    public static final String TABLE_BLOGS = "tblBlogs";
    public static final String TABLE_FAVORITE_BLOG = "tblFavoriteBlog";
    public static final String TABLE_COMMENT = "tblComment";

    public UserDBH(Context context) {
        super(context, "Login.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("CREATE TABLE IF NOT EXISTS users(username TEXT primary key, password TEXT)");

        MyDB.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_BLOGS + "(id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT, title TEXT, userID TEXT)");

        MyDB.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_FAVORITE_BLOG + "(id INTEGER PRIMARY KEY AUTOINCREMENT, userID TEXT, blogID INTEGER)");

        MyDB.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_COMMENT + "(id INTEGER PRIMARY KEY AUTOINCREMENT, userID TEXT, blogID INTEGER, comment TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("DROP TABLE IF EXISTS users");
        MyDB.execSQL("DROP TABLE IF EXISTS " + TABLE_BLOGS);
        MyDB.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE_BLOG);
        MyDB.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENT);

        onCreate(MyDB);
    }

    public Boolean insertData(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result = MyDB.insert("users", null, contentValues);
        if (result != -1) {
            return true;
        } else {
            return false;
        }
    }

    //thay đổi mật khẩu
    public boolean changePassword(String username, String oldPassword, String newPassword){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password",newPassword);
        int result = MyDB.update("users",contentValues,"username = ? AND password = ?",new String[]{username, oldPassword});
        if(result > 0){
            return true;
        }
        else {
            return false;
        }
    }

    public Boolean checkUserName(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[]{username});
        if (cursor.getCount() > 0) {
            return true;
        }
        else
            return false;
    }

    public Boolean checkUserNamePassword(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ? and password = ?", new String[] {username,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public long insertBlog(String content, String userID, String title) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("content", content);
        contentValues.put("userID", userID);
        contentValues.put("title", title);
        return MyDB.insert(TABLE_BLOGS, null, contentValues);
    }

    public int updateBlog(int blogId, String content, String title) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("content", content);
        contentValues.put("title", title);
        return MyDB.update(TABLE_BLOGS, contentValues, "id=?", new String[]{String.valueOf(blogId)});
    }

    public int deleteBlog(int blogId) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        return MyDB.delete(TABLE_BLOGS, "id=?", new String[]{String.valueOf(blogId)});
    }

    public List<BlogItem> getAllBlogs() {
        List<BlogItem> blogList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_BLOGS, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String userID = cursor.getString(cursor.getColumnIndexOrThrow("userID"));
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String content = cursor.getString(cursor.getColumnIndexOrThrow("content"));

                BlogItem blogItem = new BlogItem(id, userID, title, content);
                blogList.add(blogItem);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return blogList;
    }

    public BlogItem getBlogById(int blogId) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        String[] projection = {"id", "content", "userID","title"};
        String selection = "id=?";
        String[] selectionArgs = {String.valueOf(blogId)};

        Cursor cursor = MyDB.query(TABLE_BLOGS, projection, selection, selectionArgs, null, null, null);

        BlogItem blogItem = null;

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String content = cursor.getString(cursor.getColumnIndexOrThrow("content"));
            String userID = cursor.getString(cursor.getColumnIndexOrThrow("userID"));
            String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));

            blogItem = new BlogItem(id, userID, title, content);

            cursor.close();
        }

        return blogItem;
    }

    public long addComment(String userID, int blogId, String comment) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userID", userID);
        contentValues.put("blogID", blogId);
        contentValues.put("comment", comment);
        return MyDB.insert(TABLE_COMMENT, null, contentValues);
    }

    public int removeComment(int commentId) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        return MyDB.delete(TABLE_COMMENT, "id=?", new String[]{String.valueOf(commentId)});
    }

    public int modifyComment(int commentId, String newComment) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("comment", newComment);
        return MyDB.update(TABLE_COMMENT, contentValues, "id=?", new String[]{String.valueOf(commentId)});
    }

    public List<Comment> showComments(int blogId) {
        List<Comment> commentList = new ArrayList<>();
        SQLiteDatabase MyDB = this.getReadableDatabase();
        String[] projection = {"id", "userID", "blogID", "comment"};
        String selection = "blogID=?";
        String[] selectionArgs = {String.valueOf(blogId)};

        Cursor cursor = MyDB.query(TABLE_COMMENT, projection, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String userID = cursor.getString(cursor.getColumnIndexOrThrow("userID"));
                String blogID = cursor.getString(cursor.getColumnIndexOrThrow("blogID"));
                String comment = cursor.getString(cursor.getColumnIndexOrThrow("comment"));

                Comment commentItem = new Comment(id, comment, blogID, userID);
                commentList.add(commentItem);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return commentList;
    }

    public Cursor showComment(int commentId) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        String[] projection = {"id", "userID", "blogID", "comment"};
        String selection = "id=?";
        String[] selectionArgs = {String.valueOf(commentId)};

        return MyDB.query(TABLE_COMMENT, projection, selection, selectionArgs, null, null, null);
    }



    /*public int modifyCommentUser(int commentId, String newComment, String userID) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        String[] projection = {"userID"};
        String selection = "id=?";
        String[] selectionArgs = {String.valueOf(commentId)};

        Cursor cursor = MyDB.query(TABLE_COMMENT, projection, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            String originalUserID = cursor.getString(cursor.getColumnIndexOrThrow("userID"));

            if (originalUserID.equals(userID)) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("comment", newComment);
                return MyDB.update(TABLE_COMMENT, contentValues, "id=?", new String[]{String.valueOf(commentId)});
            } else {
                return -1;
            }
        } else {
            return 0;
        }
    }*/

    public int checkAndUpdateComment(int commentId, String userID, String newComment) {
        SQLiteDatabase MyDB = this.getWritableDatabase();

        Cursor cursor = showComment(commentId);

        if (cursor != null && cursor.moveToFirst()) {
            int userIDColumnIndex = cursor.getColumnIndex("userID");
            if (userIDColumnIndex >= 0) {
                String commentUserID = cursor.getString(userIDColumnIndex);
                cursor.close();

                if (commentUserID.equals(userID)) {
                    return modifyComment(commentId, newComment);
                }
            }
        }
        return 0;
    }

    public boolean getFav(String userID, int blogId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = "userID = ? AND blogID = ?";
        String[] selectionArgs = {userID, String.valueOf(blogId)};

        Cursor cursor = db.query(TABLE_FAVORITE_BLOG, null, selection, selectionArgs, null, null, null);
        boolean isFavorite = cursor.getCount() > 0;
        cursor.close();

        return isFavorite;
    }

    public long addFavorite(String userID, int blogId) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userID", userID);
        contentValues.put("blogID", blogId);
        return MyDB.insert(TABLE_FAVORITE_BLOG, null, contentValues);
    }

    public int removeFavorite(String userID, int blogId) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        String selection = "userID=? AND blogID=?";
        String[] selectionArgs = {userID, String.valueOf(blogId)};
        return MyDB.delete(TABLE_FAVORITE_BLOG, selection, selectionArgs);
    }

}