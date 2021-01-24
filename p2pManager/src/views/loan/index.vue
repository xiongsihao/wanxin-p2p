<template>
  <div class="app-container">
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
    >
      <el-table-column align="center" label="名称" prop="name" />
      <el-table-column label="金额(元)" prop="amount" align="center"></el-table-column>
      <el-table-column label="创建时间" align="center">
        <template slot-scope="scope">
          <span>{{changeDate(scope.row.createDate)}}</span>
        </template>
      </el-table-column>
      <el-table-column label="期限(月)" width="110" prop="period" align="center">
      	<template slot-scope="scope">
      	  <span>{{ scope.row.period/30 }}</span>
      	</template>
      </el-table-column>
      <el-table-column label="状态" prop="projectStatus" align="center">
				<template slot-scope="scope">
				  <span v-if="scope.row.projectStatus=='COLLECTING'">募集中</span>
					<span v-if="scope.row.projectStatus=='MISCARRY'">流标</span>
					<span v-if="scope.row.projectStatus=='REPAYING'">还款中</span>
					<span v-if="scope.row.projectStatus=='FULLY'">满标</span>
				</template>
			</el-table-column>
      <el-table-column label="年化利率" width="120" prop="annualRate" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.annualRate * 100 }}%</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="110" prop="period" align="center">
        <template slot-scope="scope">
          <el-button @click="handleClick(scope.row)" type="text" size="small">审核</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="block pageList">
      <el-pagination
        background
        @current-change="handleCurrentChange"
        :current-page="pageNo"
        layout="total, prev, pager, next"
        :total="total">
      </el-pagination>
    </div>
  </div>
</template>

<script>
import { getList, loanVerify } from '@/api/api'
import { parseTime } from './../../utils/index'
export default {
  data() {
    return {
      list: null,
      listLoading: true,
      pageNo: 1,
      pageSize: 10,
      total: 1,
      totalPage: 1
    }
  },
  created() {
     this.fetchData()
  },
  methods: {
    fetchData() {
      this.listLoading = true
      getList({
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        status:0,
        projectStatus: 'COLLECTING'
      }).then(response => {
        this.list = response.result.content
        this.total = Number(response.result.total),
        this.totalPage = Number(response.result.totalPage),
        this.listLoading = false
      })
    },
    changeDate(date){
      return parseTime(date, '{y}-{m}-{d}')
    },
    fetchVerify(id,approveStatus){
      loanVerify({id,approveStatus}).then(res => {
        if(res.code == 0) {
          if (approveStatus === 1) {
            this.$message({
              type: 'success',
              message: '通过审核!'
            });
          } else if(approveStatus === 2){
            this.$message({
              type: 'info',
              message: '拒绝审核！'
            });
          }
          this.fetchData()
        }else {
          this.$message({
            type: 'info',
            message: res.msg
          });
        }
      })
    },
    handleClick(row) {
      this.$confirm('是否通过审核?', '提示', {
        distinguishCancelAndClose: true,
        confirmButtonText: '通过',
        cancelButtonText: '拒绝',
        type: 'warning'
      }).then(() => {
        this.fetchVerify(row.id,1)
      }).catch(action => {
        if(action === 'cancel'){
          this.fetchVerify(row.id,2)
        }
      });
    },
    handleCurrentChange(val) {
      this.pageNo = val
      this.fetchData()
    }
  }
}
</script>
<style>
  .pageList{
    text-align: right;
    margin-top: 10px;
  }
</style>
