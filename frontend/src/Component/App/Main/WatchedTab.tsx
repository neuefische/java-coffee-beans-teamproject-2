import WatchedList from "./Tab/MovieList/WatchedList.tsx";
import MovieRatingType from "../../../Type/MovieRatingType.tsx";

export default function WatchedTab({data}: { data: MovieRatingType[] }) {

    return (
        <div className={"watchedTab"}>
            <h2>Already watched movies:</h2>
            <WatchedList data={data}/>

        </div>
    );
}