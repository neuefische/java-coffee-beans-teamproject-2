import {ChangeEvent} from "react";
import MovieType from "../../Type/MovieType.tsx";
import RatingType from "../../Type/RatingType.tsx";
import EditablePersonList from "./EditMovieForm/EditablePersonList.tsx";
import PersonType from "../../Type/PersonType.tsx";

export default function EditMovieForm({
                                          setMovieData,
                                          setRatingData,
                                          ratingData,
                                          movieData,
                                          actorsData,
                                          setActorsData,
                                          directorsData,
                                          setDirectorsData
                                      }: {
    setMovieData: React.Dispatch<React.SetStateAction<MovieType>>;
    setRatingData: React.Dispatch<React.SetStateAction<RatingType>>;
    ratingData: RatingType;
    movieData: MovieType;
    actorsData: PersonType[],
    setActorsData: (personData: PersonType[]) => void,
    directorsData: PersonType[],
    setDirectorsData: (personData: PersonType[]) => void,
}) {

    const handleMovieChange = (event: ChangeEvent<HTMLInputElement>) => {
        const {name, value} = event.target;
        setMovieData((prevState) => ({
            ...prevState,
            [name]: value,
        }));
    };

    const handleRatingChange = (event: ChangeEvent<HTMLInputElement>) => {
        const {name, value} = event.target;
        setRatingData((prevState) => ({
            ...prevState,
            [name]: value,
        }));
    };

    const handleRadioChange = (event: ChangeEvent<HTMLInputElement>) => {
        const {name, value} = event.target;
        const booleanValue = value === "true";
        setRatingData((prevState) => ({
            ...prevState,
            [name]: booleanValue,
        }));
    };

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

                {<EditablePersonList legend={"Directed by"} autocompletionUrl={"/api/director/autocompletion"} deleteUrl={"/api/movie-director"} movieId={movieData.id}
                                     staff={directorsData} setStaff={setDirectorsData}/>}
                {<EditablePersonList legend={"Starring"} autocompletionUrl={"/api/actor/autocompletion"} deleteUrl={"/api/movie-actor"} movieId={movieData.id}
                                     staff={actorsData} setStaff={setActorsData}/>}
            </form>
        </div>
    );
}
