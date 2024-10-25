import {useEffect, useState} from "react";
import axios from "axios";
import PersonList from "./MovieDetails/PersonList.tsx";
import MovieType from "../../../Type/MovieType.tsx";
import PersonType from "../../../Type/PersonType.tsx";

import RatingType from "../../../Type/RatingType.tsx";

export default function MovieDetails({id}: {id: string}) {
    const errorMessage = "Something went wrong";

    const [movieData, setMovieData] = useState<MovieType>();
    const [ratingData, setRatingData] = useState<RatingType>();
    const [actorsData, setActorsData] = useState<PersonType[]>([]);
    const [directorsData, setDirectorsData] = useState<PersonType[]>([]);

    const updateMovieData = function () {
        axios.get<MovieType>("/api/movie/" + id).then(
            (result) => setMovieData(result.data)
        ).catch(
            () => console.log(errorMessage)
        )
    }
    const updateRatingData= function () {
        axios.get<RatingType>("/api/rating/" + id).then(
            (result) => setRatingData(result.data)
        ).catch(
            () => console.log(errorMessage)
        )
    }
    const updateActorsData = function () {
        axios.get<PersonType[]>("/api/movie-actor/" + id).then(
            (result) => setActorsData(result.data)
        ).catch(
            () => console.log(errorMessage)
        )
    }
    const updateDirectorsData = function () {
        axios.get<PersonType[]>("/api/movie-director/" + id).then(
            (result) => setDirectorsData(result.data)
        ).catch(
            () => console.log(errorMessage)
        )
    }

    useEffect(updateMovieData, [id]);
    useEffect(updateRatingData, [id]);
    useEffect(updateActorsData, [id]);
    useEffect(updateDirectorsData, [id]);

    return (
        <div className="movie-details">
            <div className="movie-details-row movie_stats">
                <h3>Title</h3>
                <p>{movieData?.name}</p>
            </div>

            <div className="movie-details-row">
                <h3>Is it watched already?</h3>
                <p>{ratingData ? "Yes" : "No"}</p>
            </div>

            <div className="movie-details-row">
                <h3>Rating / Priority:</h3>
                <p>{ratingData?.rating}</p>
            </div>

            <div className="people">
                {directorsData && <PersonList people={directorsData} legend={"Directed by:"}/>}
                {actorsData && <PersonList people={actorsData} legend={"Starring:"}/>}
            </div>
        </div>

    );
}