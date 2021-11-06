#!/bin/bash
RED='\033[0;31m'
YELLOW='\033[0;33m'
NC='\033[0m' # No Color

DEFAULT_BRANCH=${1:-master}

function exit_error() {
  echo -e "${RED}$1${NC}"
  exit 1
}

array_contains () {
  local array="$1[@]"
  local seeking=$2
  local in=1
  for element in "${!array}"; do
    if [[ $element == $seeking ]]; then
      in=0
      break
    fi
  done
  return $in
}

function checkout_master() {
  git checkout $1 &>/dev/null || git checkout default &>/dev/null || exit_error "failed to checkout ${1} branch" 
  git pull
}

function checkout_branch() {
  git checkout $1 &>/dev/null || exit_error "failed to checkout ${1} branch" 
  git pull
}

echo -e "${YELLOW}Checking out default branch${NC}"
checkout_master $DEFAULT_BRANCH
echo ""

PRUNED=( $(git remote prune origin | grep '\[pruned\] origin'| sed 's/ \* \[pruned\] origin\///g'))
REMOTE_BRANCHES=( $(git branch -r | grep '  origin\/' | grep -v 'HEAD' | sed 's/  origin\///g' ) )

echo -e "${RED}Deleting branches pruned on remote${NC}"
for delete_branch in ${PRUNED[@]}; do
  if array_contains LOCAL_BRANCHES $delete_branch; then 
    echo "Deleting $delete_branch"
    git branch -D $delete_branch 
  fi
done

echo ""

LOCAL_BRANCHES=( $(git branch | sed 's/^  //g; s/^* //g') )

echo -e "${RED}Deleting local branches no longer on remote${NC}"
for lb in ${LOCAL_BRANCHES[@]}; do
  array_contains REMOTE_BRANCHES $lb || git branch -D $lb
done

echo ""

echo -e "${YELLOW}Cheking out all branches from remote and pull${NC}"
for remote_branch in ${REMOTE_BRANCHES[@]}; do
 echo "processing ${remote_branch}"
 checkout_branch $remote_branch
done

echo -e "${YELLOW}Returning to master branch${NC}"
checkout_master $DEFAULT_BRANCH

