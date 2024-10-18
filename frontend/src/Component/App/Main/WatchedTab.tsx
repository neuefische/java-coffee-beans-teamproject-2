import MovieList from "./Tab/MovieList.tsx";
import EditMovieForm from "../EditMovieForm.tsx";

export default function WatchedTab() {
    return (
        <div>
            WatchedTab
            <MovieList />
            <EditMovieForm/>
        </div>
    );
}