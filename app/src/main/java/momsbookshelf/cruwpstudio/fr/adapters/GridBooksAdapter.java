package momsbookshelf.cruwpstudio.fr.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import momsbookshelf.cruwpstudio.fr.R;
import momsbookshelf.cruwpstudio.fr.models.Book;

// TODO
public class GridBooksAdapter extends RecyclerView.Adapter<GridBooksAdapter.BooksHolder> {

    private List<Book> dataSet;

    public GridBooksAdapter(List<Book> dataSet) {
        this.dataSet = dataSet;
    }

    @NonNull
    @Override
    public BooksHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gridbook_item, parent, false);
        return new BooksHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksHolder holder, int position) {
        int mPosition = holder.getAdapterPosition();
        Book currentBook = dataSet.get(mPosition);
    }

    @Override
    public int getItemCount() {
        return this.dataSet.size();
    }


    class BooksHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView subtitle;
        TextView authors;
        ImageView cover;

        BooksHolder(View itemView) {
            super(itemView);
            this.cover = itemView.findViewById(R.id.cover);
            this.title = itemView.findViewById(R.id.title);
            this.subtitle = itemView.findViewById(R.id.subtitle);
        }
    }
}
