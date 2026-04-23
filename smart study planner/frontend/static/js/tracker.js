// Activity Tracker
document.addEventListener("DOMContentLoaded", function() {
    // 1. Determine module name
    let moduleName = "General Study";
    const contextEl = document.getElementById("pageContext");
    if (contextEl && contextEl.value) {
        moduleName = contextEl.value;
    } else {
        // Fallback to title
        const titleParts = document.title.split('-');
        if (titleParts.length > 1) {
            moduleName = titleParts[titleParts.length - 1].trim();
        } else {
            moduleName = document.title;
        }
    }

    const PING_INTERVAL_SEC = 30; // Track every 30 seconds
    const pingIntervalMs = PING_INTERVAL_SEC * 1000;

    function sendTrackingPulse() {
        // Efficiency Check: Only log time if the tab is currently visible
        if (document.visibilityState === 'visible') {
            const payload = JSON.stringify({
                moduleName: moduleName,
                duration: PING_INTERVAL_SEC
            });

            // Modern fetch with keepalive:true is best practice for analytics/beacons
            fetch('/api/analytics/track', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: payload,
                keepalive: true
            }).catch(err => console.error("Tracking pulse failed", err));
        }
    }

    // Ping server every X seconds
    setInterval(sendTrackingPulse, pingIntervalMs);
});
