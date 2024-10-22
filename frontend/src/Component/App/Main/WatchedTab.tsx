import MovieList from "./Tab/MovieList.tsx";
import EditMovieForm from "../EditMovieForm.tsx";
import {useFetch} from "../../../Hooks/useFetch.ts";
import MovieType from "../../../Type/MovieType.tsx";

export default function WatchedTab() {
    const {data, state } = useFetch<MovieType[]>("http://localhost:5173/api/movie/watched");
    const flatData = Array.isArray(data) ? data.flat() : [];
    return (
        <div>
            WatchedTab
            <MovieList data={flatData} />
            <EditMovieForm/>
        </div>
    );
}