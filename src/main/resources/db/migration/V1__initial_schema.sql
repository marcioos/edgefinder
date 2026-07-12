CREATE TABLE season (
    id UUID PRIMARY KEY,
    competition VARCHAR(255) NOT NULL,
    sport VARCHAR(50) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL
);

CREATE TABLE event (
    id UUID PRIMARY KEY,
    season_id UUID NOT NULL,
    home_competitor VARCHAR(255) NOT NULL,
    away_competitor VARCHAR(255) NOT NULL,
    start_time TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT fk_event_season
        FOREIGN KEY (season_id)
        REFERENCES season(id)
);

CREATE TABLE outcome (
    id UUID PRIMARY KEY,
    event_id UUID NOT NULL,
    market VARCHAR(50) NOT NULL,
    side VARCHAR(50) NOT NULL,
    CONSTRAINT fk_outcome_event
        FOREIGN KEY (event_id)
        REFERENCES event(id)
);

CREATE TABLE sportsbook (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    CONSTRAINT uk_sportsbook_name
        UNIQUE (name)
);

CREATE TABLE odds (
    id UUID PRIMARY KEY,
    sportsbook_id UUID NOT NULL,
    outcome_id UUID NOT NULL,
    decimal_odds NUMERIC(10,4) NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT fk_odds_sportsbook
        FOREIGN KEY (sportsbook_id)
        REFERENCES sportsbook(id),
    CONSTRAINT fk_odds_outcome
        FOREIGN KEY (outcome_id)
        REFERENCES outcome(id),
    CONSTRAINT uk_odds_sportsbook_outcome
            UNIQUE (sportsbook_id, outcome_id)
);

CREATE INDEX idx_event_season
    ON event(season_id);

CREATE INDEX idx_outcome_event
    ON outcome(event_id);

CREATE INDEX idx_odds_sportsbook
    ON odds(sportsbook_id);

CREATE INDEX idx_odds_outcome
    ON odds(outcome_id);
