import EditMovieForm from "../EditMovieForm.tsx";
import {useFetch} from "../../../Hooks/useFetch.ts";
import MovieType from "../../../Type/MovieType.tsx";
import WishList from "./Tab/MovieList/WishList.tsx";

export default function WishlistTab() {
    const {data, state } = useFetch<MovieType>("http://localhost:5173/api/movie/wishlist");
    if (state === "loading") {
        return <div>Loading...</div>;
    }

    return (
        <div>
            WishlistTab
            <WishList data={data} />
            <EditMovieForm/>
        </div>
    );
}