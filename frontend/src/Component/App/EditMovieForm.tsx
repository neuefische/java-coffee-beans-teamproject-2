import { ChangeEvent, useEffect, useState } from "react";
import MovieType from "../../Type/MovieType.tsx";
import RatingType from "../../Type/RatingType.tsx";
import PersonType from "../../Type/PersonType.tsx";
import axios from "axios";
import PersonList from "./Details/MovieDetails/PersonList.tsx";

export default function EditMovieForm({
                                          id,
                                          setMovieData,
                                          setRatingData,
                                          ratingData,
                                          movieData,
                                      }: {
    id: string | undefined;
    setMovieData: React.Dispatch<React.SetStateAction<MovieType>>;
    setRatingData: React.Dispatch<React.SetStateAction<RatingType>>;
    ratingData: RatingType;
    movieData: MovieType;
}) {
    const errorMessage = "Something went wrong";

    const [actorsData, setActorsData] = useState<PersonType[]>([]);
    const [directorsData, setDirectorsData] = useState<PersonType[]>([]);

    const handleMovieChange = (event: ChangeEvent<HTMLInputElement>) => {
        const { name, value } = event.target;
        setMovieData((prevState) => ({
            ...prevState,
            [name]: value,
        }));
    };

    const handleRatingChange = (event: ChangeEvent<HTMLInputElement>) => {
        const { name, value } = event.target;
        setRatingData((prevState) => ({
            ...prevState,
            [name]: value,
        }));
    };

    const handleRadioChange = (event: ChangeEvent<HTMLInputElement>) => {
        const { name, value } = event.target;
        const booleanValue = value === "true";
        setRatingData((prevState) => ({
            ...prevState,
            [name]: booleanValue,
        }));
    };

    const updateActorsData = function () {
        axios
            .get<PersonType[]>("/api/movie-actor/" + id)
            .then((result) => setActorsData(result.data))
            .catch(() => console.log(errorMessage));
    };

    const updateDirectorsData = function () {
        axios
            .get<PersonType[]>("/api/movie-director/" + id)
            .then((result) => setDirectorsData(result.data))
            .catch(() => console.log(errorMessage));
    };

    useEffect(updateActorsData, [id]);
    useEffect(updateDirectorsData, [id]);

    return (
        <div className="edit_form-inner">
            <form>
                <div className="form-row">
                    <label htmlFor="title">Title</label>
                    <input
                        type="text"
                        name="name"
                        value={movieData?.name}
                        onChange={(e) => handleMovieChange(e)}
                    />
                </div>
                <div className="form-row">
                    <label>Watched already?</label>
                    <div className="radio-group">
                        <label>
                            <input
                                type="radio"
                                name="isWatched"
                                value="true"
                                checked={ratingData?.isWatched === true}
                                onChange={(e) => handleRadioChange(e)}
                            />
                            Yes
                        </label>
                        <label>
                            <input
                                type="radio"
                                name="isWatched"
                                value="false"
                                checked={ratingData?.isWatched === false}
                                onChange={(e) => handleRadioChange(e)}
                            />
                            No
                        </label>
                    </div>
                </div>
                <div className="form-row">
                    <label htmlFor="rating">Rating</label>
                    <input
                        type="text"
                        name="rating"
                        value={ratingData?.rating}
                        onChange={(e) => handleRatingChange(e)}
                    />
                </div>
                {directorsData && (
                    <PersonList people={directorsData} legend={"Directed by"} />
                )}
                {actorsData && <PersonList people={actorsData} legend={"Starring"} />}
            </form>
        </div>
    );
}
