package vn.iostar.baitap04.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Song {
    private String mCode;
    private String mTitle;
    private String mLyric;
    private String mArtist;
}
