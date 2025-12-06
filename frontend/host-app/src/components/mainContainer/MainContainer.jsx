import React from "react";

export function MainContainer({children}) {
    return (
        <div id="main_container">
            <div id="background_image"></div>
            <div id="blur"></div>
            {children}
        </div>
    );
}
