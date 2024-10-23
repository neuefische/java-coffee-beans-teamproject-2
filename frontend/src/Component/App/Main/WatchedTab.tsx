import {useFetch} from "../../../Hooks/useFetch.ts";
import WatchedList from "./Tab/MovieList/WatchedList.tsx";
import MovieRatingType from "../../../Type/MovieRatingType.tsx";

export default function WatchedTab() {
    const {data, state} = useFetch<MovieRatingType>("http://localhost:5173/api/movie/watched");
    if (state === "loading") {
        return <div>Loading...</div>;
    }
    return (
        <div className={"watchedTab"}>
            <h2>Already watched movies:</h2>
            <WatchedList data={data}/>
        </div>
    );
}