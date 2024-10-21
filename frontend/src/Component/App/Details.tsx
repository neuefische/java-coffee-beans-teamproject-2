import MovieDetails from "./Details/MovieDetails.tsx";
import MovieControls from "./Details/MovieControls.tsx";
import {useState} from "react";
import EditMovieForm from "./EditMovieForm.tsx";

export default function Details() {
    const [editModeEnabled, setEditModeEnabled] = useState<boolean>(false);

    return (
        <div>
            Details
            {editModeEnabled ? <EditMovieForm/> : <MovieDetails/>}
            <MovieControls editModeEnabled={editModeEnabled} setEditModeEnabled={setEditModeEnabled}/>
        </div>
    );
}