import WatchedTab from "./Main/WatchedTab.tsx";
import WishlistTab from "./Main/WishlistTab.tsx";
import EditMovieForm from "./EditMovieForm.tsx";
import CreateButton from "./Main/CreateButton.tsx";
import {useState} from "react";
import MovieType from "../../Type/MovieType.tsx";
import RatingType from "../../Type/RatingType.tsx";

export default function Main() {

    const [movieData, setMovieData] = useState<MovieType>({
        id: "",
        name: ""
    });
    const [ratingData, setRatingData] = useState<RatingType>({
        id: "",
        rating: 0,
        isWatched: true,
        movieId: ""
    });

    return (
        <>
            <div className={"main_container"}>
                <WatchedTab/>
                <WishlistTab/>
            </div>
            <EditMovieForm id={undefined} movieData={movieData} ratingData={ratingData} setMovieData={setMovieData} setRatingData={setRatingData}/>
            <CreateButton movieData={movieData} ratingData={ratingData}/>
        </>
    );
}