package vn.iostar.baitap05;

public class NotesModel {
    private int IdNote;  // Viết hoa chữ cái đầu ??
    private String NameNote;

    public NotesModel(int idNote, String nameNote) {
        IdNote = idNote;
        NameNote = nameNote;
    }

    public int getIdNote() {
        return IdNote;
    }

    public void setIdNote(int idNote) {
        IdNote = idNote;
    }

    public String getNameNote() {
        return NameNote;
    }

    public void setNameNote(String nameNote) {
        NameNote = nameNote;
    }
}
