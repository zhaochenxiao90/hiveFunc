# hiveFunc



add jar /home/hive/hiveFunc-1-jar-with-dependencies.jar;

create temporary function SuggestData as 'suggest.data.handle.SuggestData';

select SuggestData('search-caicai','1101eef8ff2d4f226d7968aeec1e1a91','search_type',a.vid,a.sumplay,b.cn_name) from (select vid, count(1) sumplay from data_sum.sum_user_uuid_play_day where dt='20170212' group by vid )a  inner join (select vid,cn_name from dwd.dwd_cont_letv_video_day where dt='20170212')b on a.vid=b.vid;
