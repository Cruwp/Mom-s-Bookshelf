/*
 * HomeFragment
 * Fragment principal (MainActivity)
 */
package momsbookshelf.cruwpstudio.fr.fragments;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import momsbookshelf.cruwpstudio.fr.R;
import momsbookshelf.cruwpstudio.fr.adapters.GridBooksAdapter;
import momsbookshelf.cruwpstudio.fr.divers.GridSpacingItemDecoration;
import momsbookshelf.cruwpstudio.fr.models.Book;

/**
 * Fragment principal de l'application
 * Affiche les derniers livres ajoutés  #TODO lazyload
 */
public class HomeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);


        RecyclerView booksListView = view.findViewById(R.id.booksList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
        mLayoutManager.offsetChildrenHorizontal(4);
        mLayoutManager.offsetChildrenVertical(4);
        booksListView.setLayoutManager(mLayoutManager);
        booksListView.addItemDecoration(new GridSpacingItemDecoration(3, 2, false, 0));
        booksListView.setHasFixedSize(true);

        // TEMP #TODO
        List<Book> bookList = new ArrayList<>(23);
        for (int i = 0; i < 23; i++) {
            bookList.add(new Book());
        }
        booksListView.setAdapter(new GridBooksAdapter(bookList));

        return view;
    }
}
