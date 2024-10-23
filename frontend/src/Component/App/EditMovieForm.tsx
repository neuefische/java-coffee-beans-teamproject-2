import {ChangeEvent, useEffect, useState} from "react";
import MovieType from "../../Type/MovieType.tsx";
import RatingType from "../../Type/RatingType.tsx";
import PersonType from "../../Type/PersonType.tsx";
import axios from "axios";
import PersonList from "./Details/MovieDetails/PersonList.tsx";

export default function EditMovieForm({id, setMovieData, setRatingData, ratingData, movieData}:
                                          {
                                              id: string | undefined,
                                              setMovieData: React.Dispatch<React.SetStateAction<MovieType>>,
                                              setRatingData: React.Dispatch<React.SetStateAction<RatingType>>,
                                              ratingData: RatingType, movieData: MovieType
                                          }
) {

    const errorMessage = "Something went wrong";

    const [actorsData, setActorsData] = useState<PersonType[]>([]);
    const [directorsData, setDirectorsData] = useState<PersonType[]>([]);


    const handleMovieChange = (event: ChangeEvent<HTMLInputElement>) => {
        const {name, value} = event.target;
        setMovieData(prevState => ({
            ...prevState,
            [name]: value,
        }));
    }

    const handleRatingChange = (event: ChangeEvent<HTMLInputElement>) => {
        const {name, value} = event.target;
        setRatingData(prevState => ({
            ...prevState,
            [name]: value,
        }));
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

    useEffect(updateActorsData, [id]);
    useEffect(updateDirectorsData, [id]);

    return (
        <div className={"edit_form"}>
            <form>
                <label htmlFor="title">Title</label>
                <input type="text" name="name" value={movieData?.name} onChange={(e) => handleMovieChange(e)}/>
                <label htmlFor="is-watched">Is watched</label>
                <input type="text" name="isWatched" value={String(ratingData?.isWatched)}
                       onChange={(e) => handleRatingChange(e)}/>
                <label htmlFor="rating">Rating</label>
                <input type="text" name="rating" value={ratingData?.rating} onChange={(e) => handleRatingChange(e)}/>
                {directorsData && <PersonList people={directorsData} legend={"Directed by"}/>}
                {actorsData && <PersonList people={actorsData} legend={"Starring"}/>}
            </form>
        </div>
    );
}