import MovieList from "./Tab/MovieList.tsx";
import EditMovieForm from "../EditMovieForm.tsx";

export default function WishlistTab() {
    return (
        <div>
            WishlistTab
            <MovieList />
            <EditMovieForm/>
        </div>
    );
}