package com.kamael.nplp_api.model;

public class ResultDTO {
    private Long id;
    private Integer points;
    private Integer numberOfTry;
    private Boolean useHint;
    private PlayerDTO player;
    private DailyDTO daily;

    public ResultDTO(Long id, Integer points, Integer numberOfTry, Boolean useHint, PlayerDTO player, DailyDTO daily) {
        this.id = id;
        this.points = points;
        this.numberOfTry = numberOfTry;
        this.useHint = useHint;
        this.player = player;
        this.daily = daily;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getNumberOfTry() {
        return numberOfTry;
    }

    public void setNumberOfTry(Integer numberOfTry) {
        this.numberOfTry = numberOfTry;
    }

    public Boolean getUseHint() {
        return useHint;
    }

    public void setUseHint(Boolean useHint) {
        this.useHint = useHint;
    }

    public PlayerDTO getPlayer() {
        return player;
    }

    public void setPlayer(PlayerDTO player) {
        this.player = player;
    }

    public DailyDTO getDaily() {
        return daily;
    }

    public void setDaily(DailyDTO daily) {
        this.daily = daily;
    }
    
    public static ResultDTO convertToDTO(Result result) {
        if (result == null)
            return null;
        System.out.println('\n' + "DEBUG ::: " + result.getNumberOfTry() + '\n');

        Daily daily = result.getDaily();
        DailyDTO dailyDTO = null;
        
        if (daily != null)
        	dailyDTO = new DailyDTO(daily.getId(), daily.getCreateFor(), null, null);

        Player player = result.getPlayer();
        PlayerDTO playerDTO = new PlayerDTO(
            	player.getId(),
            	player.getName(),
            	player.getEmail(),
            	player.getRole(),
            	player.getPoints(),
            	player.getCreateAt(),
            	player.getLastEditAt(), 
            	player.getIsConfirmed(),
            	null
        );
        
        return new ResultDTO(
        	result.getId(),
        	result.getPoints(),
        	result.getNumberOfTry(),
        	result.getUseHint(),
        	playerDTO,
        	dailyDTO
        );
    }
}
