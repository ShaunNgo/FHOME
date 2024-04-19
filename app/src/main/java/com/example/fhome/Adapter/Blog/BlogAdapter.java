package com.example.fhome.Adapter.Blog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fhome.DBHelper.UserDBH;
import com.example.fhome.Model.BlogItem;
import com.example.fhome.R;
import com.example.fhome.UI.CommentActivity;
import com.example.fhome.UI.EditBlogActivity;

import java.util.List;

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.BlogViewHolder> {
    private List<BlogItem> blogList;

    UserDBH dbHelper;

    Context context;

    public BlogAdapter(List<BlogItem> blogList, Context context) {
        this.blogList = blogList;
        this.context = context;
    }

    @NonNull
    @Override
    public BlogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_blog, parent, false);
        return new BlogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BlogViewHolder holder, int position) {
        dbHelper = new UserDBH(context);

        BlogItem blogItem = blogList.get(position);

        holder.tvUser.setText(blogItem.getUserID());
        holder.tvContent.setText(blogItem.getContent());
        holder.tvTitle.setText(blogItem.getTitle());

        if (checkFav(blogItem)) {
            holder.btnFav.setBackgroundResource(R.drawable.baseline_favorite_24_filled);
        } else {
            holder.btnFav.setBackgroundResource(R.drawable.baseline_favorite_border_24);
        }

        holder.btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkFav(blogItem)) {
                    holder.btnFav.setBackgroundResource(R.drawable.baseline_favorite_24_filled);
                    dbHelper.removeFavorite(blogItem.getUserID(), blogItem.getId());
                    Toast.makeText(context, "Xóa yêu thích", Toast.LENGTH_SHORT).show();
                } else {
                    holder.btnFav.setBackgroundResource(R.drawable.baseline_favorite_border_24);
                    dbHelper.addFavorite(blogItem.getUserID(), blogItem.getId());
                    Toast.makeText(context, "Thêm yêu thích", Toast.LENGTH_SHORT).show();
                }

                if (checkFav(blogItem)) {
                    holder.btnFav.setBackgroundResource(R.drawable.baseline_favorite_24_filled);
                } else {
                    holder.btnFav.setBackgroundResource(R.drawable.baseline_favorite_border_24);
                }
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xóa blog");
                builder.setMessage("Bạn muốn xóa blog này?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper.deleteBlog(blogItem.getId());
                        blogList.remove(blogItem);
                        notifyDataSetChanged();
                        Toast.makeText(context, "Delete "+blogItem.getTitle(), Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();

            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditBlogActivity.class);
                intent.putExtra("blogID",String.valueOf(blogItem.getId()));
                context.startActivity(intent);
            }
        });

        int commentQuantity = dbHelper.showComments(blogItem.getId()).size();
        holder.quantityComment.setText(String.valueOf(commentQuantity));

        holder.btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommentActivity.class);
                intent.putExtra("blogID",String.valueOf(blogItem.getId()));
                intent.putExtra("userID",blogItem.getUserID());
                intent.putExtra("title",blogItem.getTitle());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return blogList.size();
    }

    public class BlogViewHolder extends RecyclerView.ViewHolder {
        public ImageButton btnFav;
        public CardView btnComment;
        public TextView tvContent, tvTitle, quantityComment, tvUser;

        public  ImageButton btnEdit, btnDelete;


        public BlogViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUser = itemView.findViewById(R.id.tvUser);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            btnFav = itemView.findViewById(R.id.favBtn);
            btnComment = itemView.findViewById(R.id.btnComment);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            quantityComment = itemView.findViewById(R.id.quantityComment);
        }
    }


    public boolean checkFav(BlogItem blogItem){
        return dbHelper.getFav(blogItem.getUserID(), blogItem.getId());
    }
}
