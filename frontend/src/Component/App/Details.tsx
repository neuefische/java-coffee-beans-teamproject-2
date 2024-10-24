import MovieDetails from "./Details/MovieDetails.tsx";
import MovieControls from "./Details/MovieControls.tsx";
import {useEffect, useState} from "react";
import EditMovieForm from "./EditMovieForm.tsx";
import "../../Style/Details.css"
import {useParams} from "react-router-dom";
import MovieType from "../../Type/MovieType.tsx";
import RatingType from "../../Type/RatingType.tsx";
import axios from "axios";

export default function Details() {

    const errorMessage = "Something went wrong";

    const [editModeEnabled, setEditModeEnabled] = useState<boolean>(false);
    const {id} = useParams();

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

    const updateMovieData = function () {
        axios.get<MovieType>("/api/movie/" + id).then(
            (result) => setMovieData(result.data)
        ).catch(
            () => console.log(errorMessage)
        )
    }
    const updateRatingData = function () {
        axios.get<RatingType>("/api/rating/" + id).then(
            (result) => setRatingData(result.data)
        ).catch(
            () => console.log(errorMessage)
        )
    }


    useEffect(updateMovieData, [id]);
    useEffect(updateRatingData, [id]);

    return (
        <div>
            {editModeEnabled ?
                <EditMovieForm id={id} setMovieData={setMovieData} setRatingData={setRatingData}
                               movieData={movieData} ratingData={ratingData}/> :
                <MovieDetails id={id ?? ""}/>}
            <MovieControls editModeEnabled={editModeEnabled} setEditModeEnabled={setEditModeEnabled}
                           ratingData={ratingData} movieData={movieData}/>
        </div>
    );
}