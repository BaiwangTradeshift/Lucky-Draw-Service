package com.baiwangmaoyi.luckydraw.rest;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.baiwangmaoyi.luckydraw.dto.NameList;
import com.baiwangmaoyi.luckydraw.dto.ResultDTO;
import com.baiwangmaoyi.luckydraw.entity.DrawResult;
import com.baiwangmaoyi.luckydraw.entity.Participant;
import com.baiwangmaoyi.luckydraw.service.NY2017Service;

@Path("2017ny")
public class NY2017Resource {

    @Autowired
    private NY2017Service ny2017Service;

    @POST
    @Path("newdrawround")
    public Response startNewDrawRound() {
        this.ny2017Service.startNewDrawRound();
        return Response.ok().build();
    }

    @GET
    @Path("currentdrawround")
    public String currentDrawRound() {
        return String.valueOf(this.ny2017Service.getCurrentDrawRound());

    }

    @GET
    @Path("drawresult")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ResultDTO> getAllPrizeResult() {
        return ny2017Service.getAllPrize();
    }

    @POST
    @Path("3rdprize")
    @Produces(MediaType.APPLICATION_JSON)
    public NameList draw3rdPrize() {
        List<Participant> participantsList = ny2017Service.draw3rdPrize(1);
        return convert2NameList(participantsList);
    }

    @POST
    @Path("2ndbprize")
    @Produces(MediaType.APPLICATION_JSON)
    public NameList draw2ndbPrize() {
        List<Participant> participantsList = ny2017Service.draw2ndBPrize(1);
        return convert2NameList(participantsList);
    }

    @POST
    @Path("2ndaprize/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public NameList draw2ndaPrize(@FormParam("fpdm") String fpdm) {
        Participant participant = ny2017Service.draw2ndAPrize(fpdm);
        NameList nameList = new NameList();
        nameList.getNames().add(participant.getDisplayName());
        return nameList;
    }

    @POST
    @Path("1stbprize")
    @Produces(MediaType.APPLICATION_JSON)
    public NameList draw1stbPrize() {
        List<Participant> participantsList = ny2017Service.draw1stBPrize(1);
        return convert2NameList(participantsList);
    }

    @POST
    @Path("1staprize/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public NameList draw1staPrize(@FormParam("fpdm") String fpdm) {
        Participant participant = ny2017Service.draw1stAPrize(fpdm);
        NameList nameList = new NameList();
        nameList.getNames().add(participant.getDisplayName());
        return nameList;
    }

    @POST
    @Path("newgameround")
    public Response startNewGameRound() {
        this.ny2017Service.startNewGameRound();
        return Response.ok().build();
    }

    @GET
    @Path("currentgameround")
    public String currentGameRound() {
        return String.valueOf(this.ny2017Service.getCurrentGameRound());
    }

    @POST
    @Path("game1")
    @Produces(MediaType.APPLICATION_JSON)
    public NameList startGame1() {
        List<Participant> participantsList = this.ny2017Service.drawGame1();
        return convert2NameList(participantsList);
    }

    @POST
    @Path("game2")
    @Produces(MediaType.APPLICATION_JSON)
    public NameList startGame2() {
        List<Participant> participantsList = this.ny2017Service.drawGame2();
        return convert2NameList(participantsList);
    }

    private NameList convert2NameList(List<Participant> participantsList) {
        List<Participant> sortedList = participantsList.stream().sorted((a, b) -> {
            if (a.getFirstName().length() == b.getFirstName().length()) {
                int fnCmp = a.getFirstName().compareTo(b.getFirstName());
                if (fnCmp == 0) {
                    if (a.getLastName().length() == b.getLastName().length()) {
                        return a.getLastName().compareTo(b.getLastName());
                    } else {
                        return a.getLastName().length() - b.getLastName().length();
                    }
                } else {
                    return fnCmp;
                }
            } else {
                return a.getFirstName().length() - b.getFirstName().length();
            }
        }).collect(Collectors.toList());
        NameList nameList = new NameList();
        List<String> list = nameList.getNames();
        for (Participant participant : sortedList) {
            list.add(participant.getDisplayName());
        }
        return nameList;
    }
}
