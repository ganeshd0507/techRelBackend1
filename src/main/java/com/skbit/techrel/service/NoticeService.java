package com.skbit.techrel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skbit.techrel.entity.Notice;
import com.skbit.techrel.exception.NotFoundException;
import com.skbit.techrel.repo.NoticeRepository;
import com.skbit.techrel.util.ApiResponse;

@Service
public class NoticeService {

	@Autowired
	private NoticeRepository noticeRepository;
	
	public Notice Create(Notice notice) {
		return noticeRepository.save(notice);
	}
	
	public Notice update(Notice notice) {
		Notice createdNotice = noticeRepository.findById(notice.getId())
				.orElseThrow(()->new NotFoundException("Notice not found"));
		createdNotice.setNoticedate(notice.getNoticedate());
		createdNotice.setMessage(notice.getMessage());
		createdNotice.setImg(notice.getImg());
		
		return noticeRepository.save(createdNotice);
	}
	
	public Notice findById(Long id) {
		return noticeRepository.findById(id)
				.orElseThrow(()->new NotFoundException("notice not found with this id"+ id));
		
	}
	
	public List<Notice> findAll(){
		return noticeRepository.findAll();
	}
	
	
	public ApiResponse delete(Long id) {
		Notice notice =noticeRepository.findById(id) 
		.orElseThrow(()->new NotFoundException("Notice not found"));
		 noticeRepository.delete(notice);
		  return ApiResponse.builder().message("Notice deleted successfully").status(true).build(); 
	}
}
