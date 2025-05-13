package com.kamael.nplp_api.model;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class DailyDTO {
	private Long id;
	private LocalDate createFor;
	private CutDTO cut;
	private List<ResultDTO> results;

	public DailyDTO(Long id, LocalDate createFor, CutDTO cut, List<ResultDTO> results) {
		this.id = id;
		this.createFor = createFor;
		this.cut = cut;
		this.results = results;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getCreateFor() {
		return createFor;
	}

	public void setCreateFor(LocalDate createFor) {
		this.createFor = createFor;
	}

	public CutDTO getCut() {
		return cut;
	}

	public void setCut(CutDTO cut) {
		this.cut = cut;
	}

	public List<ResultDTO> getResults() {
		return results;
	}

	public void setResults(List<ResultDTO> results) {
		this.results = results;
	}

	public static DailyDTO convertToDTO(Daily daily) {
		if (daily == null) {
			return null;
		}

		List<ResultDTO> resultDTOs = daily.getResults().stream()
				.map(result -> new ResultDTO(result.getId(), result.getPoints(), result.getNumberOfTry(), result.getUseHint(), null, null))
				.collect(Collectors.toList());

		Cut cut = daily.getCut();
		Song song = cut.getSong();
		Difficulty diff = cut.getDifficulty();
		CutDTO cutDTO = new CutDTO(
				cut.getId(), 
				cut.getSearchLyric(), 
				cut.getBeforeLyric(), 
				cut.getAfterLyric(), 
				new SongDTO(
					song.getId(), 
					song.getTitle(),
					null, 
					song.getPublishAt(), 
					song.getDuration(), 
					song.getAuthors().stream()
					    .map(x -> new AuthorDTO(x.getId(), x.getLastname(), x.getNickname(), x.getFirstname(), x.getBirthday(), null))
					    .collect(Collectors.toList()),
				    null
				),
				new DifficultyDTO(
					diff.getId(), 
					diff.getLibelle(), 
					diff.getPoint(), 
					null), 
				null);

		return new DailyDTO(
			daily.getId(), 
			daily.getCreateFor(), 
			cutDTO,
			resultDTOs
		);
	}
}
