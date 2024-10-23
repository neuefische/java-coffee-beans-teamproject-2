import MovieList from "./Tab/MovieList.tsx";
import EditMovieForm from "../EditMovieForm.tsx";
import {useFetch} from "../../../Hooks/useFetch.ts";
import MovieType from "../../../Type/MovieType.tsx";

export default function WishlistTab() {
    const {data, state } = useFetch<MovieType>("http://localhost:5173/api/movie/wishlist");
    return (
        <div>
            WishlistTab
            <MovieList data={data} />
            <EditMovieForm/>
        </div>
    );
}