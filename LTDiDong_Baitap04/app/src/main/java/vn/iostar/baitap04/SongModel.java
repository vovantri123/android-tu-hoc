package vn.iostar.baitap04;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SongModel {
    private String mCode;
    private String mTitle;
    private String mLyric;
    private String mArtist;
}
