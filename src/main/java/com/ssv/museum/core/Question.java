/**
 * Class representing a question
 * Also contains the logic of constructing a QR-code based on an ID
 * @author simonarneson
 */
package com.ssv.museum.core;

import com.ssv.museum.persistence.AbstractEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@XmlRootElement
public class Question extends AbstractEntity {
    @Setter
    @Getter
    private String question;
    @Setter
    @Getter
    private int points;
    @Setter
    @Getter
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="QUIZ_ID")
    private Quiz quiz;
    @Setter
    @Getter
    @OneToMany(mappedBy="question", cascade = CascadeType.PERSIST, fetch=FetchType.LAZY)
    private List<AnswerOption> options = new ArrayList<>();
    @Setter
    @Getter
    @OneToMany(mappedBy="question",cascade = CascadeType.PERSIST, fetch=FetchType.LAZY)
    private List<Media> media =  new ArrayList<>();
    @Setter
    @Getter
    @OneToOne
    private AnswerOption correctOption;

    public Question() {
    }

    public Question(String question,int points, List<AnswerOption> options, AnswerOption correctOption) {
        this.question = question;
        this.points = points;
        this.options = options;
        this.correctOption = correctOption;
    }
    public Question(String question, List<AnswerOption> options, AnswerOption correctOption) {
        this(question, 1,options,correctOption);
    }
    public Question(String question,int points, List<AnswerOption> options, AnswerOption correctOption, List<Media> media) {
        this(question, points,options,correctOption);
        this.media = media;
    }
    
    public boolean checkAnswer(AnswerOption answer){
        return correctOption.equals(answer);
    }

    @Override
    public String toString() {
        return "Question{" + "correctAnswer=" + correctOption.toString()+'}';
    }
    //Method which generates a QR code based on the question ID using Zxing
    public BufferedImage getQR() {
        try {
            //set pixel width/height of qr-code
            int size = 700;
            Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix byteMatrix = qrCodeWriter.encode("museumsjakten:"+getId().toString(),BarcodeFormat.QR_CODE, size, size, hintMap);
            int CrunchifyWidth = byteMatrix.getWidth();
            BufferedImage image = new BufferedImage(CrunchifyWidth, CrunchifyWidth,
                    BufferedImage.TYPE_INT_RGB);
            image.createGraphics();
            Graphics2D graphics = (Graphics2D) image.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, CrunchifyWidth, CrunchifyWidth);
            graphics.setColor(Color.BLACK);
            //draws the generated bit pattern to a Buffered Image graphic
            for (int i = 0; i < CrunchifyWidth; i++) {
                for (int j = 0; j < CrunchifyWidth; j++) {
                    if (byteMatrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }
            return image;
        } catch (WriterException ex) {
            Logger.getLogger(Question.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
