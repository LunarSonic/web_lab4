import {useEffect, useState} from "react";

export const useErrorVisibility = (errorMessage) => {
    const [showError, setShowError] = useState(false);

    useEffect(() => {
        if (!errorMessage) return;
        setShowError(false);
        setTimeout(() => setShowError(true), 10);
        setTimeout(() => setShowError(false), 4000);
    }, [errorMessage]);

    return showError;
}
