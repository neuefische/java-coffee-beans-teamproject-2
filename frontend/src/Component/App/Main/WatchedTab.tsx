import EditMovieForm from "../EditMovieForm.tsx";
import {useFetch} from "../../../Hooks/useFetch.ts";
import MovieType from "../../../Type/MovieType.tsx";
import WatchedList from "./Tab/MovieList/WatchedList.tsx";

export default function WatchedTab() {
    const {data, state } = useFetch<MovieType>("http://localhost:5173/api/movie/watched");
    if (state === "loading") {
        return <div>Loading...</div>;
    }
    return (
        <div>
            WatchedTab
            <WatchedList data={data} />
            <EditMovieForm/>
        </div>
    );
}