import EditMovieForm from "../EditMovieForm.tsx";
import {useFetch} from "../../../Hooks/useFetch.ts";
import WatchedList from "./Tab/MovieList/WatchedList.tsx";
import MovieRatingType from "../../../Type/MovieRatingType.tsx";

export default function WatchedTab() {
    const {data, state } = useFetch<MovieRatingType>("http://localhost:5173/api/movie/watched");
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