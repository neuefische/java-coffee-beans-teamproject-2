import {ChangeEvent, useEffect, useState} from 'react';
import PersonType from "../../../Type/PersonType.tsx";
import axios from "axios";

const AutoCompleteInput = ({autocompletionUrl, person, setPerson}
                               :
                               {
                                   autocompletionUrl: string,
                                   person: PersonType
                                   setPerson: (person: PersonType) => void
                               }
) => {
    const [suggestions, setSuggestions] = useState<PersonType[]>([]);
    const [filteredSuggestions, setFilteredSuggestions] = useState<PersonType[]>([]);
    const [showSuggestions, setShowSuggestions] = useState(false);

    const getSuggestions = function (prefix: string) {
        axios.get<PersonType[]>(`${autocompletionUrl}/${prefix}`).then(
            (result) => setSuggestions(result.data)
        )
    }

    const updateRenderedSuggestions = function () {
        const filtered = suggestions.filter(suggestion =>
            suggestion.name.toLowerCase().startsWith(person.name.toLowerCase())
        );

        setFilteredSuggestions(filtered);
        setShowSuggestions(true);
    }

    const handleChange = (event: ChangeEvent<HTMLInputElement>) => {
        const userInput = event.target.value;
        setPerson(
            {
                name: userInput,
                id: ""
            }
        );
        if (userInput) getSuggestions(userInput);
    };

    const handleClick = (suggestion: PersonType) => {
        setPerson(
            {
                name: suggestion.name,
                id: suggestion.id
            }
        );
        setFilteredSuggestions([]);
        setShowSuggestions(false);
    };

    useEffect(updateRenderedSuggestions, [suggestions]);

    return (
        <div>
            <input
                type="text"
                value={person.name}
                onChange={handleChange}
                onFocus={() => setShowSuggestions(true)}
            />
            {showSuggestions && person.name && (
                <ul>
                    {filteredSuggestions.map((suggestion, index) => (
                        <li key={index} onClick={
                            () => {
                                handleClick(suggestion)
                            }
                        }>
                            {suggestion.name}
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
};

export default AutoCompleteInput;
