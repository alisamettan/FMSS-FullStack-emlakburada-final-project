import * as React from "react";
import CircularProgress from "@mui/material/CircularProgress";
import Box from "@mui/material/Box";

export default function Loading({ size = 100 }) {
  return (
    <Box
      sx={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        height: "100vh", // Ensures full viewport height
        width: "100vw", // Ensures full viewport width
      }}
    >
      <CircularProgress size={size} />
    </Box>
  );
}
